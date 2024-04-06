package com.study.m1s11projeto.DTO;

import lombok.Data;

@Data
public class MediaGeralAlunoDTO {
    private Double mediaGeral;

    public Double getMediaGeral() {
        return mediaGeral;
    }

    public void setMediaGeral(Double mediaGeral) {
        this.mediaGeral = mediaGeral;
    }
}
