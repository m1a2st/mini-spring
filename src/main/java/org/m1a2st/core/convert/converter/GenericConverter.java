package org.m1a2st.core.convert.converter;

import java.util.Set;

/**
 * @Author m1a2st
 * @Date 2023/7/23
 * @Version v1.0
 */
public interface GenericConverter {

    Set<ConvertiblePair> getConvertibleTypes();

    Object convert(Object source, Class<?> sourceType, Class<?> targetType);

    final class ConvertiblePair {

        private final Class<?> sourceType;
        private final Class<?> targetType;

        public ConvertiblePair(Class<?> sourceType, Class<?> targetType) {
            this.sourceType = sourceType;
            this.targetType = targetType;
        }

        public Class<?> getSourceType() {
            return sourceType;
        }

        public Class<?> getTargetType() {
            return targetType;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != ConvertiblePair.class) {
                return false;
            }
            ConvertiblePair other = (ConvertiblePair) obj;
            return this.sourceType.equals(other.sourceType) && this.targetType.equals(other.targetType);

        }

        @Override
        public int hashCode() {
            return this.sourceType.hashCode() * 31 + this.targetType.hashCode();
        }
    }
}
