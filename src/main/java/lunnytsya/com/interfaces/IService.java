package lunnytsya.com.interfaces;

import lunnytsya.com.domain.BaseEntity;

public interface IService<T extends BaseEntity> {
    void save(T t);
    void delete(Long id);
    void update(T t);
}
