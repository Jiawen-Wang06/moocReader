package per.mooc.reader.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import per.mooc.reader.service.BookService;

import javax.annotation.Resource;

@Component
public class ComputeTask {
    @Resource
    private BookService bookService;
    Logger logger = LoggerFactory.getLogger(ComputeTask.class);
    @Scheduled(cron = "0 * * * * ?")
    public void updateScore(){
        logger.info("Updated the book score");
        bookService.updateScore();

    }
}