import com.example.restservice.model.Schedule
import com.example.restservice.service.ScheduleService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime

@Component
@ConfigurationProperties(prefix = "app.schedule")
@Validated
class NiiasProperties(
     val trainNumber: String, val driverNumber: String,
     val departureStation: String, val departureTime: LocalDateTime,
     val arrivalStation: String, val arrivalTime: LocalDateTime
) {

     lateinit var scheduleService: ScheduleService

     val schedule = Schedule(
          trainNumber.toInt(), driverNumber.toInt(),
          departureStation.toInt(), departureTime, arrivalStation.toInt(), arrivalTime
     )

     fun addFirstElement() {
          scheduleService.scheduleMap[trainNumber.toInt()] = schedule
     }
}