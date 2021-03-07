class OutcomeDto:
    def __init__(self,outcome, proba):
        self.outcome = outcome
        self.proba = proba*100
    def to_dict(self):
        _dict = {}
        for k, v in self.__dict__.items():
            _dict[k] = v.__dict__
        return _dict
    