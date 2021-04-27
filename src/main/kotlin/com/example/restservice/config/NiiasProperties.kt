import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
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
     var trainNumber: String, var driverNumber: String,
     var departureStation: String, var departureTime: String,
     var arrivalStation: String, var arrivalTime: String
) {

     @Autowired
     lateinit var scheduleService: ScheduleService

     val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
     val departureTimeParse: LocalDateTime = LocalDateTime.parse(departureTime, formatter)
     val arrivalTimeParse: LocalDateTime = LocalDateTime.parse(arrivalTime, formatter)

     val schedule = Schedule(
          trainNumber.toInt(), driverNumber.toInt(),
          departureStation.toInt(), departureTimeParse, arrivalStation.toInt(), arrivalTimeParse
     )

     fun addFirstElement() {
          scheduleService.scheduleMap[trainNumber.toInt()] = schedule
     }
}