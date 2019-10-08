package com.yuli.memory.client

import com.yuli.memory.domain.GetRequest

object StateContainerTypes {
  type RequestQueue = List[GetRequest]
}
