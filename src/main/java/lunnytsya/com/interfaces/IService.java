package lunnytsya.com.interfaces;

import lunnytsya.com.domain.BaseEntity;

public interface IService<T extends BaseEntity> {
    T save(T t);
    T delete(Long id);
    T update(T t);
}
