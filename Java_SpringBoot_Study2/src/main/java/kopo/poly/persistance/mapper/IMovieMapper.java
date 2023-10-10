package kopo.poly.persistance.mapper;

import kopo.poly.dto.MovieDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMovieMapper {

    int insertMovieInfo(MovieDTO pDTO) throws Exception;
    int deletemovieInfo(MovieDTO pDTO) throws Exception;
    List<MovieDTO> getmovieInfo(MovieDTO pDTO) throws Exception;

}
