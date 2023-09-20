package pl.wolinski.unofficialkgcapi.region.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wolinski.unofficialkgcapi.region.model.Region;
import pl.wolinski.unofficialkgcapi.region.repository.RegionRepository;

import java.util.Optional;

@Service
public class RegionManager {

    private RegionRepository regionRepository;

    @Autowired
    public RegionManager(RegionRepository regionRepository) { this.regionRepository = regionRepository; }

    public Optional<Region> findById(Long id) { return regionRepository.findById(id); }

    public Iterable<Region> findAll() { return regionRepository.findAll(); }

    public Region save(Region region) { return regionRepository.save(region); }

    public Optional<Region> findByName(String name) { return regionRepository.findByName(name); }


}
