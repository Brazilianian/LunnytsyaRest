package lunnytsya.com.interfaces;

public interface IService<T> {
    void save(T t);
    void delete(T t);
    void update(T t);
}
