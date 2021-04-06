using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebAppMC.Controllers
{
    // HTTPS :
        // https://localhost:44364/WeatherForecast      // Local Host
        // https://192.168.5.65:44364/WeatherForecast   // LAN
        // https://10.0.2.2:44364/WeatherForecast       // Emulator

    // HTTP :
        // http://localhost:51080/WeatherForecast      // Local Host
        // http://192.168.5.65:51080/WeatherForecast   // LAN
        // http://10.0.2.2:51080/WeatherForecast       // Emulator

    [ApiController]
    [Route("[controller]")]
    public class WeatherForecastController : ControllerBase
    {
        private static readonly string[] Summaries = new[]
        {
            "Freezing", "Bracing", "Chilly", "Cool", "Mild", "Warm", "Balmy", "Hot", "Sweltering", "Scorching"
        };

        private readonly ILogger<WeatherForecastController> _logger;

        public WeatherForecastController(ILogger<WeatherForecastController> logger)
        {
            _logger = logger;
        }

        [HttpGet]
        public WeatherForecast Get()
        {
            var random = new Random();

            return new WeatherForecast { 
                Date = DateTime.Now,
                TemperatureC = random.Next(-20, 55),
                Summary = Summaries[random.Next(Summaries.Length)]
            };
        }


    }
}
