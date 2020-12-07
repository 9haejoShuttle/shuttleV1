package com.shuttle.station;

import com.shuttle.domain.Station;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class StationService {
    private final StationRepository stationRepository;

    @PostConstruct //초기화 블럭
    public void initStationData() throws IOException {
        if (stationRepository.count()==0) {
            Resource resource = new ClassPathResource("subway_coordinates.csv");
            List<Station> stationList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream()
                    .map(line -> {
                        String[] textSplit = line.split(",");
                        return Station.builder()
                                .name(textSplit[0])
                                .latitude(Double.parseDouble(textSplit[1]))
                                .longitude(Double.parseDouble(textSplit[2]))
                                .build();
                    })
                    .collect(Collectors.toList());
            stationRepository.saveAll(stationList);
        }

    }

}
