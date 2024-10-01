package com.dogan.bilisim.dao.log;


import com.dogan.bilisim.domain.log.WebSocketLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebSocketLogRepository extends CrudRepository<WebSocketLog, Long> {

    WebSocketLog findBySessionId(String sessionId);
}
