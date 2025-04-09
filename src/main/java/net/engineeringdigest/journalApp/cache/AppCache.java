package net.engineeringdigest.journalApp.cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApp.Entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.Repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void init(){ // This method will be invoked when the bean will be created for it's parent class i.e. AppCache here
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : all){
            APP_CACHE.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}