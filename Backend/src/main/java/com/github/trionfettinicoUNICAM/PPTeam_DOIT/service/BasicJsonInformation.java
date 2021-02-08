package com.github.trionfettinicoUNICAM.PPTeam_DOIT.service;

import org.springframework.data.domain.Page;

public interface BasicJsonInformation<T> {
    Page<String> getPage(int page, int size);
    String getBasicJsonInformation(T object);
}
