package server.main.barberweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import server.main.barberweb.model.entitys.Agendamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AgendamentoRepository
extends JpaRepository<Agendamento, Long>, JpaSpecificationExecutor<Agendamento> {

    List<Agendamento> findByDataAndHorarioAndDisponivelTrue(
            LocalDate data,
            LocalTime horario
    );

    @Query("""
    SELECT DISTINCT a.horario
    FROM Agendamento a
    WHERE a.disponivel = true
      AND a.data = :data
    ORDER BY a.horario
""")
    List<LocalTime> findHorariosDisponiveis(LocalDate data);

}
