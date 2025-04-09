package net.engineeringdigest.journalApp.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "config_journal_app")
@Getter
@Setter
@NoArgsConstructor //Used for deserialization **
public class ConfigJournalAppEntity {
    private String key;
    private String value;
}
