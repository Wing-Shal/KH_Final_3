package com.kh.Final3.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.Final3.dto.CompanyDto;
import com.kh.Final3.vo.JoinVO;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<JoinVO, CompanyDto>() {
            @Override
            protected void configure() {
                skip(destination.getCompanyChecked());
            }
        });
        return modelMapper;
    }
}
