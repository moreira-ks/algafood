package km1.algafood.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import km1.algafood.api.assemblers.StateModelAssembler;
import km1.algafood.api.assemblers.StateInputDisassembler;
import km1.algafood.api.models.StateModel;
import km1.algafood.api.models.StateInput;
import km1.algafood.domain.services.StateRegisterService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/states")
@AllArgsConstructor
public class StateController {
  
  private final StateRegisterService registerService;
  private final StateInputDisassembler disassembler;
  private final StateModelAssembler assembler;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public StateModel saveState(@RequestBody StateInput stateInput) {
    var toRegister = disassembler.apply(stateInput);
    var registered = registerService.register(toRegister);
    var stateModel = assembler.apply(registered);
    return stateModel;
  }

  @GetMapping
  public List<StateModel> findStates() {
    var states =  registerService.fetchAll();
    var statesModel = states.stream().map(assembler).toList();
    return statesModel;
  }

  @GetMapping("/{id}")
  public StateModel findStateById(@PathVariable Long id) {
    var state =  registerService.fetchByID(id);
    var stateModel = assembler.apply(state);
    return stateModel;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteStateModelById(@PathVariable Long id) {
    registerService.remove(id);
  }

  @PutMapping("/{id}")
  public StateModel updateStateById(@PathVariable Long id,@RequestBody StateInput stateInput) {
    var toUpdate = disassembler.apply(stateInput);
    var updated =  registerService.update(id, toUpdate);
    var stateModel = assembler.apply(updated);
    return stateModel;
  }
}
