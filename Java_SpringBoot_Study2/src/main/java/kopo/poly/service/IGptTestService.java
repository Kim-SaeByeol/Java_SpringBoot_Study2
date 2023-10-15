package kopo.poly.service;

import kopo.poly.dto.GptTestDTO;

public interface IGptTestService {
    GptTestDTO chatWithGpt(GptTestDTO requestDTO);
}
