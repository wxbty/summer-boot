package ink.zfei.boot.context.properties.bind;

import ink.zfei.summer.util.Assert;
import ink.zfei.summer.util.BeanUtils;
import ink.zfei.summer.util.ObjectUtils;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class BindResult<T> {

    private static final BindResult<?> UNBOUND = new BindResult<>(null);

    private final T value;

    private BindResult(T value) {
        this.value = value;
    }

    /**
     * Return the object that was bound or throw a {@link NoSuchElementException} if no
     * value was bound.
     * @return the bound value (never {@code null})
     * @throws NoSuchElementException if no value was bound
     * @see #isBound()
     */
    public T get() throws NoSuchElementException {
        if (this.value == null) {
            throw new NoSuchElementException("No value bound");
        }
        return this.value;
    }

    /**
     * Returns {@code true} if a result was bound.
     * @return if a result was bound
     */
    public boolean isBound() {
        return (this.value != null);
    }

    /**
     * Invoke the specified consumer with the bound value, or do nothing if no value has
     * been bound.
     * @param consumer block to execute if a value has been bound
     */
    public void ifBound(Consumer<? super T> consumer) {
        Assert.notNull(consumer, "Consumer must not be null");
        if (this.value != null) {
            consumer.accept(this.value);
        }
    }

    /**
     * Apply the provided mapping function to the bound value, or return an updated
     * unbound result if no value has been bound.
     * @param <U> the type of the result of the mapping function
     * @param mapper a mapping function to apply to the bound value. The mapper will not
     * be invoked if no value has been bound.
     * @return an {@code BindResult} describing the result of applying a mapping function
     * to the value of this {@code BindResult}.
     */
    public <U> BindResult<U> map(Function<? super T, ? extends U> mapper) {
        Assert.notNull(mapper, "Mapper must not be null");
        return of((this.value != null) ? mapper.apply(this.value) : null);
    }

    /**
     * Return the object that was bound, or {@code other} if no value has been bound.
     * @param other the value to be returned if there is no bound value (may be
     * {@code null})
     * @return the value, if bound, otherwise {@code other}
     */
    public T orElse(T other) {
        return (this.value != null) ? this.value : other;
    }

    /**
     * Return the object that was bound, or the result of invoking {@code other} if no
     * value has been bound.
     * @param other a {@link Supplier} of the value to be returned if there is no bound
     * value
     * @return the value, if bound, otherwise the supplied {@code other}
     */
    public T orElseGet(Supplier<? extends T> other) {
        return (this.value != null) ? this.value : other.get();
    }


    @Deprecated
    public T orElseCreate(Class<? extends T> type) {
        Assert.notNull(type, "Type must not be null");
        return (this.value != null) ? this.value : BeanUtils.instantiateClass(type);
    }

    /**
     * Return the object that was bound, or throw an exception to be created by the
     * provided supplier if no value has been bound.
     * @param <X> the type of the exception to be thrown
     * @param exceptionSupplier the supplier which will return the exception to be thrown
     * @return the present value
     * @throws X if there is no value present
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.value == null) {
            throw exceptionSupplier.get();
        }
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return ObjectUtils.nullSafeEquals(this.value, ((BindResult<?>) obj).value);
    }

    @Override
    public int hashCode() {
        return ObjectUtils.nullSafeHashCode(this.value);
    }

    @SuppressWarnings("unchecked")
    public static <T> BindResult<T> of(T value) {
        if (value == null) {
            return (BindResult<T>) UNBOUND;
        }
        return new BindResult<>(value);
    }

}
