package com.khu.bbangting.domain.bread.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {

    private final BreadService breadService;

/*   매일 자정, 종료 할 빵팅 확인 후 tingStatus 변경
     1) 오픈 상태인 빵팅: open -> no
     2) 판매 완료된 빵팅: end -> no
 */
    @Scheduled(cron = "0 0 0 * * *")
    public void closeBBangTing() {
        log.info("closeBBangTing 스케줄러 작동 시작");
        breadService.closeBBangTing();
        log.info("closeBBangTing 스케줄러 작동 완료");
    }

/*   10~20시까지 1시간마다, 빵팅 시간 확인 후 tingStatus 변경
     1) 빵팅 오픈: comingSoon -> open
 */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void openBBangTing() {
        log.info("openBBangTing 스케줄러 작동 시작");
        breadService.openBBangTing();
        log.info("openBBangTing 스케줄러 작동 완료");
    }

/*   10~20시까지 10분마다, 재고 확인 후 tingStatus 변경
     1) 재고가 0인 빵팅: open -> end
     2) 재고가 생긴 빵팅: end -> open
 */
    @Scheduled(cron = "0 0/10 * * * *")
    public void checkBreadStock() {
        log.info("checkBreadStock 스케줄러 작동 시작");
        breadService.checkBreadStock();
        log.info("checkBreadStock 스케줄러 작동 완료");
    }

}
