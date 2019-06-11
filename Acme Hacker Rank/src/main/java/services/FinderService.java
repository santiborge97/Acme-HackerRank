
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	// Managed Repository ------------------------
	@Autowired
	private FinderRepository	finderRepository;

	// Suporting services ------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private PositionService		positionService;

	@Autowired
	private Validator			validator;


	public Finder create() {

		Finder result;

		result = new Finder();

		result.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		return result;

	}

	public Collection<Finder> findAll() {

		final Collection<Finder> result = this.finderRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Finder findOne(final int finderId) {

		final Finder result = this.finderRepository.findOne(finderId);
		return result;

	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		if (finder.getId() != 0) {
			final Actor actor = this.actorService.findByPrincipal();
			Assert.notNull(actor);

			final Authority hW = new Authority();
			hW.setAuthority(Authority.HACKER);
			if (actor.getUserAccount().getAuthorities().contains(hW)) {
				final Hacker owner = finder.getHacker();
				Assert.notNull(owner);
				Assert.isTrue(actor.getId() == owner.getId());
				finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));
				final Collection<Position> positionSearchedList = this.positionService.findPositionsByFinder(finder);
				finder.setPositions(positionSearchedList);
			}
		} else
			finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		final Finder result = this.finderRepository.save(finder);

		return result;

	}

	public Finder saveAdmin(final Finder finder) {

		final Finder result = this.finderRepository.save(finder);

		return result;
	}

	public void deletePositions(final Finder finder) {
		final Collection<Position> positions = new HashSet<Position>();
		finder.setPositions(positions);
	}

	public void deleteFinderActor(final int actorId) {

		final Finder finder = this.finderRepository.findFinderByHacker(actorId);

		this.finderRepository.delete(finder);

	}

	// Other business rules

	public Finder reconstruct(final Finder finder, final BindingResult binding) {

		final Finder finderBBDD = this.findOne(finder.getId());

		finder.setHacker(finderBBDD.getHacker());

		finder.setLastUpdate(new Date(System.currentTimeMillis() - 1000));

		this.validator.validate(finder, binding);

		return finder;

	}

	public Finder findFinderByHacker(final int hackerId) {
		final Finder finder = this.finderRepository.findFinderByHacker(hackerId);
		return finder;
	}

	public Collection<Finder> findFindersByPositionId(final int positionId) {

		final Collection<Finder> result = this.finderRepository.findFindersByPositionId(positionId);

		return result;
	}
}
