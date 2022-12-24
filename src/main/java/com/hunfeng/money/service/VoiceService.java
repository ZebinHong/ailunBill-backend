package com.hunfeng.money.service;

import com.hunfeng.money.common.DemoException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface VoiceService {
    Map<String, Object> translate(InputStream is) throws DemoException, IOException;
}
