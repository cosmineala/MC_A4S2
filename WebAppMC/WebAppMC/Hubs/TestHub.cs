using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebAppMC.Hubs
{
    public class TestHub : Hub
    {
        public async Task SendToAll( string name, string message)
        {
            System.Diagnostics.Debug.WriteLine("Name: " + name + " message: " + message);
            await Clients.All.SendAsync("ReciveAll", name, message);
        }

    }
}
