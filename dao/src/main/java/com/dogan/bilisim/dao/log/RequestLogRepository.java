package com.dogan.bilisim.dao.log;


import com.dogan.bilisim.domain.log.RequestLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends CrudRepository<RequestLog, Long> {

}
