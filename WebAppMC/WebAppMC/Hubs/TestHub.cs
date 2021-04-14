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
            Clients.All.SendAsync("ReciveAll", message);

            //Clients.Others.SendAsync("ReciveAll", message);

            string conId = Context.ConnectionId;

            System.Diagnostics.Debug.WriteLine( "Call: SendToALL | ConID: " + conId + " | ID: " + message.ID + " | Sender: " + message.Sender + " | message: " + message.Content);
        }

        public async Task SendToOthers(Message message)
        {
            Clients.Others.SendAsync("ReciveAll", message);

            string conId = Context.ConnectionId;

            System.Diagnostics.Debug.WriteLine("Call: SendToOthers | ConID: " + conId + " | ID: " + message.ID + " | Sender: " + message.Sender + " | message: " + message.Content);
        }

    }
}
