package com.eulersbridge.iEngage.core.beans;

import com.eulersbridge.iEngage.database.domain.Poll;
import com.eulersbridge.iEngage.database.domain.PollOption;
import com.eulersbridge.iEngage.database.repository.PollOptionRepository;
import com.eulersbridge.iEngage.database.repository.PollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Yikai Gong
 */
@Component
public class ScheduledTasks {
  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
  private boolean appReady = false;

  final
  PollRepository pollRepo;
  final
  PollOptionRepository pollOptionRepo;

  @Autowired
  public ScheduledTasks(PollRepository pollRepo, PollOptionRepository pollOptionRepo) {
    this.pollRepo = pollRepo;
    this.pollOptionRepo = pollOptionRepo;
  }

  @Scheduled(fixedDelay = 5000)
  public void closeExpiredPoll() {
    if (!appReady)
      return;
//    log.info("The time is now {}", dateFormat.format(new Date()));
    List<Poll> polls = pollRepo.findExpiredUnclosedPolls(System.currentTimeMillis());
    for (Poll p : polls) {
      List<PollOption> options = p.getPollOptionsList$();
      for (PollOption op : options) {
        if(op.getVoters()!=null){
          Long numOfVoters = new Integer(op.getVoters().size()).longValue();
          op.setNumOfVoters(numOfVoters);
          pollOptionRepo.save(op, 0);
        }
      }
      p.setClosed(true);
      pollRepo.save(p, 0);
    }
  }

  public boolean isAppReady() {
    return appReady;
  }

  public void setAppReady(boolean appReady) {
    this.appReady = appReady;
  }
}
