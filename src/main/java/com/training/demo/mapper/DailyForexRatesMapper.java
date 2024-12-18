package com.training.demo.mapper;

import com.training.demo.model.dto.DailyForexRatesDto;
import com.training.demo.model.entity.ForexRatesPo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface DailyForexRatesMapper {

    @Mapping(source = "Date", target = "date")
    @Mapping(source = "USD/NTD", target = "usdToNtd")
    DailyForexRatesDto jsonMapToDto(Map<String, String> jsonMap);

    List<DailyForexRatesDto> jsonMapListToDtoList(List<Map<String, String>> jsonMapList);


    @Mapping(target = "dateTime", source = "date", qualifiedByName = "stringToDateTime")
    @Mapping(target = "baseCurrency", constant = "NTD")
    @Mapping(target = "quoteCurrency", constant = "USD")
    @Mapping(target = "rates", source = "usdToNtd")
    ForexRatesPo dtoToPo (DailyForexRatesDto dto);

    @Named("stringToDateTime")
    default LocalDateTime stringToDateTime(String date) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate parsedDate = LocalDate.parse(date, inputFormatter);
        return parsedDate.atStartOfDay().plusHours(8);
    }

    List<ForexRatesPo> dtoListListToPoList(List<DailyForexRatesDto> DtoList);

}
