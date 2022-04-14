package app.sctp.persistence;

import java.util.LinkedHashMap;
import java.util.Map;

import app.sctp.app.SctApplication;

public final class Repositories {
    private static final Map<String, BaseRepository> repositoryMap = new LinkedHashMap<>();


    @SuppressWarnings("unchecked")
    static <T extends BaseRepository> T getRepository(Class<T> repositoryClass) {
        if (BaseRepository.class.isAssignableFrom(repositoryClass)) {
            T repository = (T) repositoryMap.get(repositoryClass.getName());
            if (repository == null) {
                try {
                    repository = repositoryClass.getConstructor(SctpAppDatabase.class)
                            .newInstance(SctApplication.getInstance().getAppDatabase());
                    repositoryMap.put(repositoryClass.getName(), repository);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
            return repository;
        } else {
            throw new IllegalArgumentException("Repository must extend " + BaseRepository.class);
        }
    }
}
