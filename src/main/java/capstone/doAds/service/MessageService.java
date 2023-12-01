package capstone.doAds.service;

import capstone.doAds.auth.SecurityUtils;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.Message;
import capstone.doAds.domain.MessageRoom;
import capstone.doAds.dto.MessageDto;
import capstone.doAds.dto.MessageRoomDto;
import capstone.doAds.dto.SendMessageDto;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.repository.MessageRepository;
import capstone.doAds.repository.MessageRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageRoomRepository messageRoomRepository;
    private final MemberRepository memberRepository;

    public void send(MessageRoom messageRoom, SendMessageDto sendMessageDto) {
        Message from = Message.builder().messageRoom(messageRoom)
                .sender(messageRoom.getFrom())
                .receiver(messageRoom.getTo())
                .content(sendMessageDto.getContent())
                .build();
        Message to = Message.builder().messageRoom(messageRoom.getToSend())
                .sender(messageRoom.getFrom())
                .receiver(messageRoom.getTo())
                .content(sendMessageDto.getContent())
                .build();
        messageRepository.save(from);
        messageRepository.save(to);
        messageRoom.sendMessage(from, to);
        messageRoomRepository.save(messageRoom);
        messageRoomRepository.save(messageRoom.getToSend());
    }

    public Long sendMessageByMemberId(Long memberId, SendMessageDto sendMessageDto) {
        Member from = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        Member to = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("회원 ID가 올바르지 않습니다."));
        Optional<MessageRoom> messageRoom = messageRoomRepository.findByFromAndTo(from.getId(), to.getId());
        if (messageRoom.isEmpty()) {
            MessageRoom fromMessageRoom = MessageRoom.builder().from(from).to(to).build();
            MessageRoom toMessageRoom = MessageRoom.builder().from(to).to(from).build();
            fromMessageRoom.match(toMessageRoom);
            messageRoomRepository.save(fromMessageRoom);
            messageRoomRepository.save(toMessageRoom);
            messageRoom = Optional.of(fromMessageRoom);
        }
        send(messageRoom.get(), sendMessageDto);
        return messageRoom.get().getId();
    }

    public void sendMessageByRoomId(Long roomId, SendMessageDto sendMessageDto) {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        MessageRoom messageRoom = messageRoomRepository.findByIdFetch(roomId).orElseThrow(
                () -> new IllegalArgumentException("메시지 방이 올바르지 않습니다."));
        if (member.getId() != messageRoom.getFrom().getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        send(messageRoom, sendMessageDto);
    }

    public List<MessageRoomDto> getMessageRooms() {
        Member member = memberRepository.findByEmailFetchMessageRoom(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        return member.getMessageRooms().stream().map(MessageRoomDto::new).collect(Collectors.toList());
    }

    public List<MessageDto> getMessages(Long roomId) {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        MessageRoom messageRoom = messageRoomRepository.findByIdFetchMessages(roomId).orElseThrow(
                () -> new IllegalArgumentException("메시지방이 존재하지 않습니다.."));
        messageRoom.initUnReadMessageCount();
        messageRoomRepository.save(messageRoom);
        if (member.getId() != messageRoom.getFrom().getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        return messageRoom.getMessages().stream().map(MessageDto::new).collect(Collectors.toList());
    }

    public MessageDto getMessageDetail(Long messageId) {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        Message message = messageRepository.findByIdFetch(messageId).orElseThrow(
                () -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        if (member.getId() != message.getSender().getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        message.getMessageRoom().initUnReadMessageCount();
        return new MessageDto(message);
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        Message message = messageRepository.findByIdFetch(messageId).orElseThrow(
                () -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        if (member.getId() != message.getSender().getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        messageRepository.delete(message);
    }

    @Transactional
    public void leaveMessageRoom(Long roomId) {
        Member member = memberRepository.findByEmail(SecurityUtils.getLoggedUserEmail()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다."));
        MessageRoom messageRoom = messageRoomRepository.findByIdFetchMessages(roomId).orElseThrow(
                () -> new IllegalArgumentException("메시지방을 찾을 수 없습니다."));
        if (member.getId() != messageRoom.getFrom().getId()) {
            throw new IllegalArgumentException("잘못된 접근입니다.");
        }
        for (Message message : messageRoom.getMessages()) {
            messageRepository.delete(message);
        }
        messageRoomRepository.delete(messageRoom);
    }
}
