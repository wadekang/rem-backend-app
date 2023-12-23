package com.wadekang.rem.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import com.wadekang.rem.svc.EventService;
import com.wadekang.rem.vo.CreateEventVO;
import com.wadekang.rem.vo.EventRequestVO;
import com.wadekang.rem.vo.EventVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping("/getEvents")
    public ResponseEntity<CommonResponse<List<EventVO>>> getEvents(@RequestBody EventRequestVO eventRequestVO) {

        List<EventVO> eventVOList = eventService.getEventsOfUser(eventRequestVO);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        eventVOList
                ));
    }

    @PostMapping("/createEvent")
    public ResponseEntity<CommonResponse<Map<String, Long>>> createEvent(@RequestBody CreateEventVO createEventVO) {

        Long eventId = eventService.createEvent(createEventVO);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        Map.of("eventId", eventId)
                ));
    }
}
