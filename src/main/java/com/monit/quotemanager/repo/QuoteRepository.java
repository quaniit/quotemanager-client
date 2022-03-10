package com.monit.quotemanager.repo;

import java.util.UUID;

import com.monit.quotemanager.model.QuoteImpl;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface QuoteRepository extends CrudRepository<QuoteImpl, UUID>{
    public Iterable<QuoteImpl> findBySymbol(String symbol);

    @Modifying
    @Query("delete from QuoteImpl q where q.symbol=:symbol")
    public void deleteBySymbol(@Param("symbol") String symbol);
}
