package be.dabla.model.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;

// http://agoncal.wordpress.com/2010/03/03/bean-validation-with-jpa-1-0/
public class EntityValidationEventListener {
	private final TraversableResolver resolver;
	private final Validator validator;

	public EntityValidationEventListener() {
		resolver = new EntityTraversableResolver();
		validator = Validation.buildDefaultValidatorFactory().usingContext().traversableResolver(resolver).getValidator();
	}
	
    public void validate(final Object entity) {
    	final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(entity);
    	
    	if (!constraintViolations.isEmpty()) {
        	final Set<ConstraintViolation<?>> propagatedViolations = new HashSet<ConstraintViolation<?>>(constraintViolations.size());
        	final Set<String> classNames = new HashSet<String>();
        	
            for (final ConstraintViolation<?> violation : constraintViolations) {
            	propagatedViolations.add(violation);
	            classNames.add(violation.getLeafBean().getClass().getName());
            }
            
            throw new ConstraintViolationException(new StringBuilder().append("validation failed for classes ").append(classNames).toString(), propagatedViolations);
        }
    }
}
