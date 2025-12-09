package org.example.webkachkiserver.repositrories;

import org.example.webkachkiserver.models.shorts.Shorts;

import java.util.List;

public interface ShortsRepositoryCustom {
    List<Shorts> findRandom(int size);
}
