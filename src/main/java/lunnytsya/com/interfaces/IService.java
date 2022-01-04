package lunnytsya.com.interfaces;

public interface IService<T> {
    void save(T t);
    void delete(Long id);
    void update(T t);
}
