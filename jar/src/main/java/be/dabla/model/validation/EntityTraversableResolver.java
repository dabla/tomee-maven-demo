package be.dabla.model.validation;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.TraversableResolver;

public class EntityTraversableResolver implements TraversableResolver {
	public boolean isReachable(final Object traversableObject, final Path.Node traversableProperty, final Class rootBeanType, final Path pathToTraversableObject, final ElementType elementType) {
        return (traversableObject != null);
    }

    public boolean isCascadable(final Object traversableObject, final Path.Node traversableProperty, final Class rootBeanType, final Path pathToTraversableObject, final ElementType elementType) {
        return true;
    }
}