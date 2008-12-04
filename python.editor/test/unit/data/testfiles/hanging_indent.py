class LSimpleXMLRPCServer(LSimpleXMLRPCDispatcher, SimpleXMLRPCServer):
    def __init__(self, addr, requestHandler=LSimpleXMLRPCRequestHandler,
              logRequests=1, allow_none=0):
        SimpleXMLRPCServer.__init__(self, addr, requestHandler, logRequests)
        LSimpleXMLRPCDispatcher.__init__(self)

        self.allow_none = allow_none

