package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor2 implements ItemProcessor<Person, Person>{
	
	private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);

	@Override
	public Person process(Person item) throws Exception {
		final String firstName = item.getFirstName().toLowerCase();
        final String lastName = item.getLastName().toLowerCase();

        final Person transformedPerson = new Person(firstName, lastName);

        log.info("Inside PersonItemProcessor2 -- Converting (" + item + ") into (" + transformedPerson + ")");

        return transformedPerson;
	}

}
