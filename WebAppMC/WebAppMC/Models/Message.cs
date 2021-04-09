using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebAppMC.Models
{
    public class Message
    {
        public Guid ID { get; set; }
        public String Sender { get; set; }
        public String Content { get; set; }
    }
}
