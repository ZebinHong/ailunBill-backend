package com.hunfeng.money.controller.keep;

import com.hunfeng.money.common.DemoException;
import com.hunfeng.money.common.Result;
import com.hunfeng.money.service.VoiceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/money/voice")
public class VoiceController {

    @Autowired
    private VoiceService voiceService;

    @ApiOperation("根据用户id获取账单")
    @PostMapping("translate")
    public Result translate(@RequestParam("file") MultipartFile file) throws IOException, DemoException {
        InputStream is = file.getInputStream();
        Map<String, Object> res = voiceService.translate(is);
        if (res.get("result") != null && res.get("money") != null && res.get("details") != null){
            return Result.success("语音识别成功！", res);
        }else {
            return Result.error("语音识别失败！");
        }
    }
}
