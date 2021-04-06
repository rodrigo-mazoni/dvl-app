package com.dvlcube.app.rest;

import com.dvlcube.app.interfaces.MenuItem;
import com.dvlcube.app.jpa.repo.JobRepository;
import com.dvlcube.app.manager.data.JobBean;
import com.dvlcube.app.manager.data.vo.MxRestResponse;
import com.dvlcube.utils.interfaces.rest.MxFilterableBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.dvlcube.app.manager.data.e.Menu.CONFIGURATION;
import static com.dvlcube.app.manager.data.e.Menu.STATS;

@RestController
@MenuItem(value = CONFIGURATION)
@RequestMapping("${dvl.rest.prefix}/jobs")
public class JobService implements MxFilterableBeanService<JobBean, Long> {


    @Autowired
    private JobRepository repo;

    @Override
    @GetMapping
    public Iterable<JobBean> get(Map<String, String> params) {
        return repo.firstPage();
    }

    @Override
    @GetMapping("/{id}")
    public Optional<JobBean> get(Long id) {
        return repo.findById(id);
    }

    @Override
    @PostMapping
    public MxRestResponse post(@RequestBody JobBean body) {
        JobBean jobBean = repo.save(body);
        return GenericRestResponse.ok(jobBean.getId());
    }

    @Override
    public Iterable<JobBean> getLike(String id) {
        return repo.findAllLike(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
