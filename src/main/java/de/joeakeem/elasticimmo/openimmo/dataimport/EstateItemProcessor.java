package de.joeakeem.elasticimmo.openimmo.dataimport;

import javax.inject.Inject;

import org.dozer.DozerBeanMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.joeakeem.elasticimmo.model.Estate;
import de.joeakeem.elasticimmo.openimmo.model.Immobilie;

@Component
public class EstateItemProcessor implements ItemProcessor<Immobilie, Estate> {
	
	@Inject
	private DozerBeanMapper dozerBeanMapper;

	@Override
	public Estate process(Immobilie immobilie) throws Exception {
		Estate estate = new Estate();
		dozerBeanMapper.map(immobilie, estate);
		return estate;
	}

}
