package capstone.doAds.controller;

import capstone.doAds.dto.MessageDto;
import capstone.doAds.dto.MessageRoomDto;
import capstone.doAds.dto.SendMessageDto;
import capstone.doAds.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/messages/{memberId}/send")
    public String sendMessage(@PathVariable("memberId") Long memberId, Model model) {
        SendMessageDto messageDto = new SendMessageDto("", memberId);
        model.addAttribute("messageDto", messageDto);
        return "sendMessage";
    }

    @PostMapping("/messages/{memberId}/send")
    public String sendMessageByMemberId(@PathVariable("memberId") Long memberId,
                                      @RequestParam("content") String content) {
        Long roomId = messageService.sendMessageByMemberId(memberId, new SendMessageDto(content, memberId));
        return "redirect:/messages/room/" + roomId;
    }

    @GetMapping("/messages/room/{roomId}/send")
    public String sendMessageByRoomId(@PathVariable("roomId") Long roomId, Model model) {
        SendMessageDto messageDto = new SendMessageDto("", roomId);
        model.addAttribute("messageDto", messageDto);
        return "sendMessage";
    }

    @PostMapping("/messages/room/{roomId}/send")
    public String sendMessageByRoomId(@PathVariable("roomId") Long roomId,
                                    @RequestParam("content") String content) {
        messageService.sendMessageByRoomId(roomId, new SendMessageDto(content, roomId));
        return "redirect:/messages/room/" + roomId;
    }

    @GetMapping("/messages/room")
    public String getMessageRooms(Model model) {
        List<MessageRoomDto> messageRooms = messageService.getMessageRooms();
        model.addAttribute("messageRoomDtoList", messageRooms);
        return "messageRooms";
    }

    @GetMapping("/messages/room/{roomId}")
    public String getMessages(@PathVariable("roomId") Long roomId, Model model) {
        List<MessageDto> messages = messageService.getMessages(roomId);
        model.addAttribute("messageDtoList", messages);
        model.addAttribute("roomId", roomId);
        return "messageRoom";
    }

//    @GetMapping("/messages/room/{roomId}/{messageId}")
//    public String getMessageDetail(@RequestHeader("Authorization")String accessToken,
//                                                          @PathVariable("messageId") Long messageId) {
//        try {
//            return new BaseResponse<>(messageService.getMessageDetail(messageId));
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }

//    @PostMapping("/messages/room/{roomId}/{messageId}/delete")
//    public BaseResponse<String> deleteMessage(@PathVariable("messageId") Long messageId) {
//        try {
//            messageService.deleteMessage(messageId);
//            return new BaseResponse<>("메세지 삭제 성공!");
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


//    @PostMapping("/messages/room/{roomId}/leave")
//    public BaseResponse<String> leaveMessageRoom(@RequestHeader("Authorization")String accessToken,
//                                                     @PathVariable("roomId") Long roomId) {
//        try {
//            messageService.leaveMessageRoom(roomId);
//            return new BaseResponse<>("메세지 방 나가기 성공!");
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }
}
