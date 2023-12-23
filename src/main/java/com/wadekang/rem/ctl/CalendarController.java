package com.wadekang.rem.ctl;

import com.wadekang.rem.common.vo.CommonResponse;
import com.wadekang.rem.svc.CalendarService;
import com.wadekang.rem.vo.CalendarVO;
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
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping("/getCalendars")
    public ResponseEntity<CommonResponse<List<CalendarVO>>> getCalendars() {

        List<CalendarVO> calendars = calendarService.getCalendarsOfUser();

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "OK",
                                calendars
                        )
                );
    }

    @PostMapping("/addCalendar")
    public ResponseEntity<CommonResponse<?>> addCalendar(@RequestBody CalendarVO calendarVO) {

        calendarService.createCalendar(calendarVO);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        null
                        )
                );
    }

    @PostMapping("/addSharedCalendar")
    public ResponseEntity<CommonResponse<?>> addSharedCalendar(@RequestBody Map<String, String> map) {

        calendarService.createSharedCalendar(map.get("code"));

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                                HttpStatus.OK.value(),
                                "OK",
                                null
                        )
                );
    }

    @PostMapping("/updateCalendar")
    public ResponseEntity<CommonResponse<?>> updateCalendar(@RequestBody CalendarVO calendarVO) {

        calendarService.updateCalendar(calendarVO);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        null
                        )
                );
    }

    @PostMapping("/updateSharedCalendar")
    public ResponseEntity<CommonResponse<?>> updateSharedCalendar(@RequestBody CalendarVO calendarVO) {

        calendarService.updateSharedCalendar(calendarVO);

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        null
                        )
                );
    }

    @PostMapping("/deleteCalendar")
    public ResponseEntity<CommonResponse<?>> deleteCalendar(@RequestBody CalendarVO calendarVO) {

        calendarService.deleteCalendar(calendarVO.getCalendarId());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        null
                        )
                );
    }

    @PostMapping("/deleteSharedCalendar")
    public ResponseEntity<CommonResponse<?>> deleteSharedCalendar(@RequestBody CalendarVO calendarVO) {

        calendarService.deleteSharedCalendar(calendarVO.getCalendarId());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        null
                        )
                );
    }

    @PostMapping("/generateCalendarCode")
    public ResponseEntity<CommonResponse<Map<String, String>>> generateCalendarCode(@RequestBody CalendarVO calendarVO) {

        String code = calendarService.generateCalendarCode(calendarVO.getCalendarId());

        return ResponseEntity.ok()
                .body(new CommonResponse<>(
                        HttpStatus.OK.value(),
                        "OK",
                        Map.of("code", code)
                    )
                );
    }
}
