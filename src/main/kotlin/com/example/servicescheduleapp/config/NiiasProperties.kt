import com.example.servicescheduleapp.service.ScheduleService
import com.example.servicescheduleapp.model.Schedule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
@ConstructorBinding
@ConfigurationProperties(prefix = "app.schedule")
data class NiiasProperties(
     var codOfTechnicalOperationWithTrains: String, var trainNumber: String,
     var trainIndex: String, var countTrainOnLine: String, var sequentialNumberOfBrigade: String,
     var driverNumber: String, var codeOfDepartureStation: String, var codeOfDepartureStationWithBrigade: String,
     var departureTime: String, var departureTimeWithBrigade: String,
     var codeOfArrivalStation: String, var codeOfArrivalStationWithBrigade: String, var arrivalTime: String,
     var arrivalTimeWithBrigade: String, var codeOfHeadWagon: String
) {

     @Autowired
     lateinit var scheduleService: ScheduleService

     val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     val departureTimeParse: LocalDateTime = LocalDateTime.parse(departureTime, formatter)
     val arrivalTimeParse: LocalDateTime = LocalDateTime.parse(arrivalTime, formatter)
     val departureTimeWithBrigadeParse: LocalDateTime = LocalDateTime.parse(departureTimeWithBrigade, formatter)
     val arrivalTimeWithBrigadeParse: LocalDateTime = LocalDateTime.parse(arrivalTimeWithBrigade, formatter)

     val schedule = Schedule(
          codOfTechnicalOperationWithTrains.toInt(), trainNumber.toInt(), trainIndex.toInt(),
          countTrainOnLine.toInt(), sequentialNumberOfBrigade.toInt(), driverNumber.toLong(),
          codeOfDepartureStation.toInt(), codeOfDepartureStationWithBrigade.toInt(), departureTimeParse,
          departureTimeWithBrigadeParse, codeOfArrivalStation.toInt(), codeOfArrivalStationWithBrigade.toInt(),
          arrivalTimeParse, arrivalTimeWithBrigadeParse, codeOfHeadWagon
     )

     fun addFirstElement() {
          scheduleService.scheduleMap[trainNumber.toInt()] = schedule
     }
}