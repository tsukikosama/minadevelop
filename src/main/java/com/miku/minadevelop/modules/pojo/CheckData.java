package com.miku.minadevelop.modules.pojo;

import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import lombok.Data;

@Data
public class CheckData {
    private String  id;
    private ImageCaptchaTrack data;
}