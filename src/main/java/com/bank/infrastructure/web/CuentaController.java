package com.bank.infrastructure.web;

import com.bank.application.dto.CrearCuentaRequest;
import com.bank.application.dto.CuentaDTO;
import com.bank.application.dto.MovimientoRequest;
import com.bank.application.dto.TransaccionDTO;
import com.bank.application.dto.TransferenciaRequest;
import com.bank.application.usecase.CuentaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuenta")
@Tag(name = "Cuentas", description = "Operaciones bancarias sobre cuentas")
@SecurityRequirement(name = "BearerAuth")
public class CuentaController {

    private final CuentaUseCase useCase;

    public CuentaController(CuentaUseCase useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Crear una cuenta", description = "Crea una nueva cuenta en el sistema con su saldo inicial")
    @ApiResponse(responseCode = "200", description = "Cuenta creada correctamente")
    @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    @PostMapping
    public CuentaDTO crearCuenta(@RequestBody CrearCuentaRequest request) {
        return useCase.crearCuenta(request);
    }

    @Operation(summary = "Obtener información de una cuenta", description = "Devuelve datos completos de la cuenta buscada")
    @ApiResponse(responseCode = "200", description = "Cuenta encontrada")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @GetMapping("/{numero}")
    public CuentaDTO obtenerCuenta(
            @Parameter(description = "Número de cuenta", example = "1234567890") @PathVariable String numero) {
        return useCase.obtenerCuenta(numero);
    }

    @Operation(summary = "Consultar saldo", description = "Retorna el saldo actual de la cuenta")
    @ApiResponse(responseCode = "200", description = "Saldo consultado")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @GetMapping("/{numero}/saldo")
    public CuentaDTO consultarSaldo(
            @Parameter(description = "Número de cuenta", example = "1234567890") @PathVariable String numero) {
        return useCase.consultarSaldo(numero);
    }

    @Operation(summary = "Depositar dinero", description = "Incrementa el saldo de la cuenta y registra la transacción")
    @ApiResponse(responseCode = "200", description = "Depósito realizado correctamente")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @PostMapping("/{numero}/deposito")
    public void depositar(
            @Parameter(description = "Número de cuenta", example = "1234567890") @PathVariable String numero,
            @RequestBody MovimientoRequest request) {
        useCase.depositar(numero, request);
    }

    @Operation(summary = "Retirar dinero", description = "Disminuye el saldo si hay fondos suficientes")
    @ApiResponse(responseCode = "200", description = "Retiro realizado correctamente")
    @ApiResponse(responseCode = "400", description = "Saldo insuficiente", content = @Content)
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @PostMapping("/{numero}/retiro")
    public void retirar(
            @Parameter(description = "Número de cuenta", example = "1234567890") @PathVariable String numero,
            @RequestBody MovimientoRequest request) {
        useCase.retirar(numero, request);
    }

    @Operation(summary = "Historial de movimientos", description = "Devuelve la lista de depósitos y retiros")
    @ApiResponse(responseCode = "200", description = "Historial consultado correctamente")
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @GetMapping("/{numero}/historial")
    public List<TransaccionDTO> historial(
            @Parameter(description = "Número de cuenta", example = "1234567890") @PathVariable String numero) {
        return useCase.historial(numero);
    }

    @Operation(summary = "Transferir entre cuentas", description = "Transfiere fondos entre dos cuentas")
    @ApiResponse(responseCode = "200", description = "Transferencia realizada correctamente")
    @ApiResponse(responseCode = "400", description = "Saldo insuficiente o datos inválidos", content = @Content)
    @ApiResponse(responseCode = "404", description = "Cuenta no encontrada", content = @Content)
    @PostMapping("/transferencia")
    public void transferir(@RequestBody TransferenciaRequest request) {
        useCase.transferir(request);
    }
}
