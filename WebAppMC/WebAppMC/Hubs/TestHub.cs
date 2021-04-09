using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebAppMC.Models;

namespace WebAppMC.Hubs
{
    public class TestHub : Hub
    {
        public async Task SendToAll( Message message )
        {
            await Clients.All.SendAsync("ReciveAll", message);

            System.Diagnostics.Debug.WriteLine("ID: " + message.ID + " | Sender: " + message.Sender + " | message: " + message.Content);
        }

    }
}
