package com.paulbutcher

import akka.actor._
import akka.routing.RoundRobinRouter

case class WorkItem()

class Worker extends Actor {

  def receive = {
    case WorkItem => Thread.sleep(10000)
  }
}

object AkkaBoundedMailboxTimeout extends App {

  val system = ActorSystem("AkkaBoundedMailboxTimeout")

  val workers = system.actorOf(Props[Worker].
    withRouter(RoundRobinRouter(7)).
    withDispatcher("worker-dispatcher"), "worker")

  while (true)
    workers ! WorkItem
}