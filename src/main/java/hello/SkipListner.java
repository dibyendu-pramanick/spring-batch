package hello;

import org.springframework.batch.core.SkipListener;

public class SkipListner implements SkipListener<Person, Person>{

	@Override
	public void onSkipInRead(Throwable t) {
		System.out.println("---------------inside onSkipInRead-----------");
		
	}

	@Override
	public void onSkipInWrite(Person item, Throwable t) {
		System.out.println("---------------inside onSkipInWrite-----------");
		
	}

	@Override
	public void onSkipInProcess(Person item, Throwable t) {
		System.out.println("---------------inside onSkipInProcess-----------");
		System.out.println("-----------"+item.getFirstName());
		
	}

}
