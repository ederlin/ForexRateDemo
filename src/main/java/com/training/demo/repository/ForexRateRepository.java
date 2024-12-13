package com.training.demo.repository;

import com.training.demo.model.entity.ForexRatesPo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForexRateRepository extends MongoRepository<ForexRatesPo, String> {

  Optional<ForexRatesPo> findByDateTime(LocalDateTime dateTime);

  List<ForexRatesPo> findByDateTimeBetweenAndQuoteCurrency(LocalDate startDate, LocalDate endDate, String currency);


}
