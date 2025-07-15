package com.dailynuts.member.controller;

import com.dailynuts.member.dto.MemberRequestDto;
import com.dailynuts.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<Long> createMember(@RequestBody @Valid MemberRequestDto req){

        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(req));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }
}
